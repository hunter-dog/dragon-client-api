package com.dragan.emuson.api.dragonlake.service;

import com.dragan.emuson.api.aws.service.S3UploadService;
import com.dragan.emuson.api.dragonlake.domain.*;
import com.dragan.emuson.api.dragonlake.dto.*;
import com.dragan.emuson.api.dragonlake.repository.DragonLakeRepository;
import com.dragan.emuson.api.file.domain.FileMeta;
import com.dragan.emuson.api.file.domain.YongsayFiles;
import com.dragan.emuson.api.file.service.FileService;
import com.dragan.emuson.api.yongniverse.domain.Yongniverse;
import com.dragan.emuson.api.yongniverse.dto.YongniverseResponse;
import com.dragan.emuson.common.util.CommonUtil;
import com.dragan.emuson.exception.BadRequestException;
import com.google.gson.*;
import com.querydsl.jpa.impl.JPAQuery;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.BiPredicate;
import java.util.function.Predicate;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class DragonLakeService {

    private final DragonLakeRepository dragonLakeRepository;
    private final S3UploadService s3UploadService;
    private final FileService fileService;

    @Transactional
    public void submitYongsay(MultipartFile file, YongsaySubmitRequest yongsaySubmitRequest) {
        FileMeta fileMeta = fileService.saveFileMeta(file);

        Category category = dragonLakeRepository.findCategoryById(yongsaySubmitRequest.getCategoryId());
        Yongsay yongsay = Yongsay.builder()
                .content(yongsaySubmitRequest.getContent())
                .category(category)
                .winCount(0)
                .build();

        dragonLakeRepository.saveYongsay(yongsay);

        YongsayFiles yongsayFiles = YongsayFiles.builder()
                .yongsay(yongsay)
                .fileMeta(fileMeta)
                .build();

        dragonLakeRepository.saveYongsayFiles(yongsayFiles);

        try {
            s3UploadService.saveFile(file, fileMeta.getUniqueName());
        } catch (IOException e) {
            log.error("error {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    public List<CategoryResponse> getCategories() {
        List<Category> categories = dragonLakeRepository.findCategories();

        return categories.stream()
                .map((category) -> {
                    CategoryResponse res = new CategoryResponse();
                    res.setValue(category.getId());
                    res.setLabel(category.getName());
                    return res;
                })
                .collect(Collectors.toList());
    }

    public RandomYongsayResponse getRandomYongsay(Long currentId) {
        Yongsay randomYongsay = dragonLakeRepository.findRandomYongsay(currentId);

        return RandomYongsayResponse.builder()
                .id(randomYongsay.getId())
                .content(randomYongsay.getContent())
                .categoryName(randomYongsay.getCategory().getName())
                .fileName(randomYongsay.getYongsayFiles().get(0).getFileMeta().getUniqueName())
                .build();
    }

    public List<RandomYongsayResponse> getRandomYongsays(int count) {
        List<Yongsay> randomYongsays = dragonLakeRepository.findRandomYongsays(count);
        return randomYongsays.stream()
                .map((yongsay) -> RandomYongsayResponse.builder()
                        .id(yongsay.getId())
                        .content(yongsay.getContent())
                        .categoryName(yongsay.getCategory().getName())
                        .fileName(yongsay.getYongsayFiles().get(0).getFileMeta().getUniqueName())
                        .build())
                .collect(Collectors.toList());
    }

    @Transactional
    public void saveExam(YongeicSaveRequest yongeicSaveRequest) {

        Yongeic newExam = Yongeic.builder()
                .examName(yongeicSaveRequest.getExamName())
                .writer(yongeicSaveRequest.getWriter())
                .totalQuestionCount(yongeicSaveRequest.getExam().size())
                .scorePerfect(yongeicSaveRequest.getScorePerfect())
                .likes(0)
                .createdAt(LocalDateTime.now())
                .build();

        Yongeic exam = dragonLakeRepository.saveExam(newExam);
        int sortOrder = 1;
        for (QuestionSaveRequest question : yongeicSaveRequest.getExam()) {
            YongeicQuestion newQuestion = YongeicQuestion.builder()
                    .type(question.getType())
                    .score(question.getScore())
                    .title(question.getTitle())
                    .content(question.getContent())
                    .choice(question.getChoice())
                    .quizAnswer(question.getQuizAnswer())
                    .keywordAnswer(question.getKeywordAnswer())
                    .sortOrder(sortOrder)
                    .yongeic(exam)
                    .build();

            dragonLakeRepository.saveQuestion(newQuestion);
            sortOrder += 1;
        }
    }

    public ExamResponse loadExam(Long examId, final Boolean showAnswer) {
        Yongeic exam = dragonLakeRepository.findExamById(examId);

        List<ExamQuestion> examQuestions = exam.getQuestions().stream()
                .map((question) -> {
                    Optional<Map<String, Object>> quizOptions = Optional.ofNullable(question.getChoice())
                            .map(choice -> JsonParser.parseString(choice).getAsJsonObject())
                            .map(CommonUtil::getMapFromJsonObject);

                    Predicate<YongeicQuestion> showQuizAnswer = (q) -> (q.getType().equals("QUIZ") || q.getType().equals("OX")) && showAnswer;
                    Predicate<YongeicQuestion> showKeywordAnswer = (q) -> q.getType().equals("KEYWORD") && showAnswer;

                    return ExamQuestion.builder()
                            .id(question.getId())
                            .type(question.getType())
                            .score(question.getScore())
                            .title(question.getTitle())
                            .content(question.getContent())
                            .choice(quizOptions.orElse(null))
                            .quizAnswer(showQuizAnswer.test(question) ? question.getQuizAnswer() : null )
                            .keywordAnswer(showKeywordAnswer.test(question) ? question.getKeywordAnswer() : null )
                            .build();
                })
                .collect(Collectors.toList());

        return ExamResponse.builder()
                .examId(exam.getId())
                .examName(exam.getExamName())
                .writer(exam.getWriter())
                .questions(examQuestions)
                .build();
    }

    public List<ExamWithStatistic> getExamList() {

        return dragonLakeRepository.findAllExams();
//        return dragonLakeRepository.findAllExams()
//                .stream()
//                .map((exam) -> ExamResponse.builder()
//                        .examId(exam.getId())
//                        .examName(exam.getExamName())
//                        .writer(exam.getWriter())
//                        .likes(exam.getLikes())
//                        .totalQuestionCount(exam.getTotalQuestionCount())
//                        .build()
//                )
//                .collect(Collectors.toList());
    }

    @Transactional
    public YongeicResult gradeYongeic(YongeicSubmitRequest yongeicSubmitRequest) {
        Yongeic exam = dragonLakeRepository.findExamById(yongeicSubmitRequest.getExamId());
        List<YongeicQuestion> originalQuestions = exam.getQuestions();

        int totalScore = 0;
        int index = 0;

        for (YongeicUserAnswer userAnswer : yongeicSubmitRequest.getAnswers()) {
            YongeicQuestion comparisonTarget = originalQuestions.get(index);

            YongeicQuestionStatistic.YongeicQuestionStatisticBuilder statisticBuilder = YongeicQuestionStatistic.builder()
                    .yongeicQuestion(comparisonTarget);

            if (userAnswer.getQuestionType().equals("QUIZ") || userAnswer.getQuestionType().equals("OX")) {
                if (userAnswer.getQuizAnswer().equals(comparisonTarget.getQuizAnswer())) {
                    totalScore += comparisonTarget.getScore();
                }

                YongeicQuestionStatistic questionStatistic = dragonLakeRepository.findYongeicQuestionStatisticByQuizAnswer(comparisonTarget.getId(), userAnswer.getQuizAnswer());
                if (questionStatistic != null) {
                    questionStatistic.increaseSelectedCount();
                } else {
                    YongeicQuestionStatistic newStatistic = statisticBuilder
                            .userQuizAnswer(userAnswer.getQuizAnswer())
                            .selectedCount(1)
                            .build();

                    dragonLakeRepository.saveYongeicQuestionStatistic(newStatistic);
                }
            }

            if (userAnswer.getQuestionType().equals("KEYWORD")) {
                if (userAnswer.getKeywordAnswer().equals(comparisonTarget.getKeywordAnswer())) {
                    totalScore += comparisonTarget.getScore();
                }

                YongeicQuestionStatistic questionStatistic = dragonLakeRepository.findYongeicQuestionStatisticByKeywordAnswer(comparisonTarget.getId(), userAnswer.getKeywordAnswer());
                if (questionStatistic != null) {
                    questionStatistic.increaseSelectedCount();
                } else {
                    YongeicQuestionStatistic newStatistic = statisticBuilder
                            .userKeywordAnswer(userAnswer.getKeywordAnswer())
                            .selectedCount(1)
                            .build();

                    dragonLakeRepository.saveYongeicQuestionStatistic(newStatistic);
                }
            }

            index += 1;
        }

        YongeicStatistic examResult = YongeicStatistic.builder()
                .scoreAcquired(totalScore)
                .scorePerfect(exam.getScorePerfect())
                .yongeic(exam)
                .build();

        dragonLakeRepository.saveExamResult(examResult);

        return YongeicResult.builder()
                .scoreAcquired(examResult.getScoreAcquired())
                .scorePerfect(examResult.getScorePerfect())
                .scoreAverage(dragonLakeRepository.findAvgYongeicScore(exam.getId()))
                .build();
    }

    @Transactional
    public YongniverseResponse addLikesOfYongniverse(UpdateLieksRequest updateLieksRequest) {
        // todo 240210: Yongniverse 서비스 쪽으로 옮기기
        Yongniverse yongniverse = dragonLakeRepository.findYongniverseById(updateLieksRequest.getId());
        yongniverse.addLikes();

        return YongniverseResponse.builder()
                .likes(yongniverse.getLikes())
                .build();
    }

    @Transactional
    public YongniverseResponse addDislikesOfYongniverse(UpdateLieksRequest updateLieksRequest) {
        Yongniverse yongniverse = dragonLakeRepository.findYongniverseById(updateLieksRequest.getId());
        yongniverse.addDislikes();

        return YongniverseResponse.builder()
                .dislikes(yongniverse.getDislikes())
                .build();
    }

    @Transactional
    public void updateLikesHistory(UpdateLieksRequest updateLieksRequest, HttpServletRequest request) {
        String clientIp = request.getRemoteAddr();
        String tableName = updateLieksRequest.getTableName();
        Long targetId = updateLieksRequest.getId();
        Boolean isLike = updateLieksRequest.getIsLike();

        LikeHistory likeHistory = dragonLakeRepository.findLikeHistoryByIp(clientIp, updateLieksRequest.getIsLike());

        if (likeHistory != null) {
            JsonObject jsonObject = JsonParser.parseString(likeHistory.getHistory()).getAsJsonObject();
            JsonArray idListJson = jsonObject.getAsJsonArray(tableName);

            List<Long> idList = new ArrayList<>();
            for (JsonElement element : idListJson) {
                idList.add(element.getAsLong());
            }

            if (idList.contains(targetId)) {
                throw new BadRequestException("추천/비추천은 하루에 한 번만 가능합니다.");
            }

            idList.add(targetId);
            jsonObject.add(tableName, CommonUtil.convertLongListToJsonArray(idList));

            likeHistory.updateHistory(new Gson().toJson(jsonObject));
        } else {
            JsonObject jsonObject = new JsonObject();
            JsonArray jsonArray = new JsonArray();

            jsonArray.add(targetId);

            jsonObject.add(tableName, jsonArray);

            LikeHistory newLikeHistory = LikeHistory.builder()
                    .ip(clientIp)
                    .history(new Gson().toJson(jsonObject))
                    .isLike(isLike)
                    .build();

            dragonLakeRepository.saveLikeHistory(newLikeHistory);
        }
    }

    public Long getTotalCountOfYongsays() {
        return dragonLakeRepository.findTotalCountOfYongsays();
    }

    public Page<GuestBookResponse> getGuestBook(Pageable pageable, String orderBy) {
        List<GuestBookResponse> totalGuestBook = dragonLakeRepository.findTotalGuestBook(pageable, orderBy);
        JPAQuery<Long> countQuery = dragonLakeRepository.getGuestBookCountQuery();

        return PageableExecutionUtils.getPage(totalGuestBook, pageable, countQuery::fetchCount);
    }

    public List<GuestBookResponse> getChildCommentOfGuestBook(Long parentId) {
        return dragonLakeRepository.findChildCommentOfGuestBook(parentId);
    }

    @Transactional
    public void writeCommentInGuestBook(GuestBookCommentRequest guestBookCommentRequest) {

        GuestBook newComment = GuestBook.builder()
                .writer(guestBookCommentRequest.getWriter())
                .password(guestBookCommentRequest.getPassword())
                .ip(guestBookCommentRequest.getIp())
                .depth(guestBookCommentRequest.getDepth())
//                .seq(dragonLakeRepository.findNextSeqInGuestBook(guestBookCommentRequest.getDepth()))
                .content(guestBookCommentRequest.getContent())
                .avatarUrl(guestBookCommentRequest.getAvatarUrl())
                .createdAt(LocalDateTime.now())
                .build();

        dragonLakeRepository.saveGuestBookComment(newComment);
    }

    @Transactional
    public List<GuestBookResponse> writeChildComment(GuestBookCommentRequest guestBookCommentRequest) {

        GuestBook parent = dragonLakeRepository.findCommentById(guestBookCommentRequest.getParentId());

        GuestBook newComment = GuestBook.builder()
                .parent(parent)
                .writer(guestBookCommentRequest.getWriter())
                .password(guestBookCommentRequest.getPassword())
                .ip(guestBookCommentRequest.getIp())
                .depth(guestBookCommentRequest.getDepth())
//                .seq(dragonLakeRepository.findNextSeqInGuestBook(guestBookCommentRequest.getDepth()))
                .content(guestBookCommentRequest.getContent())
                .avatarUrl(guestBookCommentRequest.getAvatarUrl())
                .createdAt(LocalDateTime.now())
                .build();

        dragonLakeRepository.saveGuestBookComment(newComment);

        return dragonLakeRepository.findChildCommentOfGuestBook(parent.getId());
    }


    @Transactional
    public WinningYongsayStatistics updateYongsay(Long yongsayId) {
        Yongsay yongsay = dragonLakeRepository.findYongsayById(yongsayId);
        yongsay.increaseWinCount();

        Integer totalWinningCount = dragonLakeRepository.findTotalWinningCountOfYongsays(yongsayId);

        Double winningRate = (double) Math.round(((double) yongsay.getWinCount() / totalWinningCount) * 1000) / 10;

        WinningYongsayStatistics winningYongsayStatistics = new WinningYongsayStatistics();
        winningYongsayStatistics.setWinningCount(yongsay.getWinCount());
        winningYongsayStatistics.setTotalWinningCount(totalWinningCount);
        winningYongsayStatistics.setWinningRate(winningRate);

        return winningYongsayStatistics;
    }

    public List<QuestionStatisticResponse> getQuestionStatistic(Long questionId) {

        List<YongeicQuestionStatistic> questionStatistics = dragonLakeRepository.findYongeicQuestionStatisticByQuestionId(questionId);
        Integer totalSelectedCount = dragonLakeRepository.findTotalSelectedCountOfQuestion(questionId);

        return questionStatistics.stream()
                .map((item) -> {
                    QuestionStatisticResponse res = new QuestionStatisticResponse();
                    if (item.getYongeicQuestion().getType().equals("KEYWORD")) {
                        res.setKeywordAnswer(item.getUserKeywordAnswer());
                    } else {
                        res.setQuizAnswer(item.getUserQuizAnswer());
                    }

                    Double selectedRate = (double) Math.round(((double) item.getSelectedCount() / totalSelectedCount) * 1000) / 10;

                    res.setSelectedCount(item.getSelectedCount());
                    res.setTotalSelectedCount(totalSelectedCount);
                    res.setSelectedRate(selectedRate);

                    return res;
                }).collect(Collectors.toList());
    }
    @Transactional
    public GuestBookResponse addLikesOfGuestBook(UpdateLieksRequest updateLieksRequest) {
        GuestBook guestBook = dragonLakeRepository.findGuestBookById(updateLieksRequest.getId());
        guestBook.addLikes();

        return GuestBookResponse.builder()
                .likes(guestBook.getLikes())
                .build();
    }

    @Transactional
    public GuestBookResponse addDislikesOfGuestBook(UpdateLieksRequest updateLieksRequest) {
        GuestBook guestBook = dragonLakeRepository.findGuestBookById(updateLieksRequest.getId());
        guestBook.addDislikes();

        return GuestBookResponse.builder()
                .dislikes(guestBook.getDislikes())
                .build();
    }
}
