package com.dragan.emuson.api.dragonlake.controller;

import com.dragan.emuson.api.dragonlake.dto.*;
import com.dragan.emuson.api.dragonlake.service.DragonLakeService;
import com.dragan.emuson.common.Response;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;

@RestController
@RequiredArgsConstructor
public class DragonLakeController {

    private final DragonLakeService dragonLakeService;

    @GetMapping("/test")
    public Response test() {
        String hibernateVersion = org.hibernate.Hibernate.class.getPackage().getImplementationVersion();
        System.out.println("Hibernate version: " + hibernateVersion);

        System.out.println("테스트");
        try {
            Thread.sleep(3000L);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return Response.ofSuccess("테스트", null);
    }

    @PostMapping("/yongsay/submit")
    public Response submitYongsay(
            @RequestPart("file") MultipartFile file,
            @RequestPart("jsonData") @Validated YongsaySubmitRequest yongsaySubmitRequest) {

        dragonLakeService.submitYongsay(file, yongsaySubmitRequest);
        return Response.ofSuccess("정상적으로 업로드 되었습니다.", null);
    }

    @GetMapping("/yongsay/count")
    public Response getTotalCountOfYongsays() {
        return Response.ofSuccess(dragonLakeService.getTotalCountOfYongsays());
    }

    @GetMapping("/yongsay/categories")
    public Response getCategories() {
        return Response.ofSuccess(dragonLakeService.getCategories());
    }

    @GetMapping("/yongsay/random")
    public Response getRandomYongsay(@RequestParam(name = "id") Optional<Long> id) {
        return Response.ofSuccess(dragonLakeService.getRandomYongsay(id.orElse(null)));
    }

    @GetMapping("/yongsay/contest/random")
    public Response getRandomYongsays(@RequestParam(name = "count") int count) {
        return Response.ofSuccess(dragonLakeService.getRandomYongsays(count));
    }

    @PostMapping("/yongeic/save")
    public Response saveYongeic(@Validated @RequestBody YongeicSaveRequest yongeicSaveRequest) {
        dragonLakeService.saveExam(yongeicSaveRequest);
        return Response.ofSuccess("정상적으로 저장 되었습니다. (관리자 승인 후 사용 가능)", null);
    }

    @GetMapping("/yongeic/list")
    public Response getExamList() {
        return Response.ofSuccess(dragonLakeService.getExamList());
    }

    @GetMapping("/yongeic/load/{id}")
    public Response loadExam(@PathVariable("id") Long examId, HttpServletRequest request) {
        Boolean showAnswer = Optional.ofNullable(request.getParameter("showAnswer")).isPresent();
        return Response.ofSuccess(dragonLakeService.loadExam(examId, showAnswer));
    }

    @PostMapping("/yongeic/submit")
    public Response submitExam(@RequestBody YongeicSubmitRequest yongeicSubmitRequest) {
        return Response.ofSuccess(dragonLakeService.gradeYongeic(yongeicSubmitRequest));
    }

    @PostMapping("/yongniverse/add/likes")
    public Response addLikesOfYongniverse(@RequestBody UpdateLieksRequest updateLieksRequest, HttpServletRequest request) {
        dragonLakeService.updateLikesHistory(updateLieksRequest, request);
        return Response.ofSuccess(dragonLakeService.addLikesOfYongniverse(updateLieksRequest));
    }

    @PostMapping("/yongniverse/add/dislikes")
    public Response addDislikesOfYongniverse(@RequestBody UpdateLieksRequest updateLieksRequest, HttpServletRequest request) {
        dragonLakeService.updateLikesHistory(updateLieksRequest, request);
        return Response.ofSuccess(dragonLakeService.addDislikesOfYongniverse(updateLieksRequest));
    }

    @PostMapping("/board/guest/book/add/likes")
    public Response addLikesOfGuestBook(@RequestBody UpdateLieksRequest updateLieksRequest, HttpServletRequest request) {
        dragonLakeService.updateLikesHistory(updateLieksRequest, request);
        return Response.ofSuccess(dragonLakeService.addLikesOfGuestBook(updateLieksRequest));
    }

    @PostMapping("/board/guest/book/add/dislikes")
    public Response addDislikesOfGuestBook(@RequestBody UpdateLieksRequest updateLieksRequest, HttpServletRequest request) {
        dragonLakeService.updateLikesHistory(updateLieksRequest, request);
        return Response.ofSuccess(dragonLakeService.addDislikesOfGuestBook(updateLieksRequest));
    }

    @GetMapping("/board/guest/book")
    public Response getGuestBook(Pageable pageable, HttpServletRequest request) {
        String orderBy = request.getParameter("orderBy");
        return Response.ofSuccessWithPaging(dragonLakeService.getGuestBook(pageable, orderBy));
    }

    @GetMapping("/board/guest/book/{id}/comment")
    public Response getChildCommentOfGuestBook(@PathVariable("id") Long parentId) {
        return Response.ofSuccess(dragonLakeService.getChildCommentOfGuestBook(parentId));
    }

    @PostMapping("/board/guest/book/write/comment")
    public Response writeCommentInGuestBook(@Validated @RequestBody GuestBookCommentRequest guestBookCommentRequest, HttpServletRequest request) {
        guestBookCommentRequest.setIp(request.getRemoteAddr());

        if (guestBookCommentRequest.getParentId() == null)  {
            dragonLakeService.writeCommentInGuestBook(guestBookCommentRequest);
            return Response.ofSuccess("정상적으로 업로드 되었습니다.", null);
        }
        return Response.ofSuccess(dragonLakeService.writeChildComment(guestBookCommentRequest));
    }

    @PatchMapping("/yongsay/contest/winner/increment/{id}")
    public Response increaseCountOfYongsayContest(@PathVariable("id") Long yongsayId) {
        return Response.ofSuccess(dragonLakeService.updateYongsay(yongsayId));
    }

//    @GetMapping("/yongsay/contest/winner/statistics/{id}")
//    public Response getWinningYongsayStatistics(@PathVariable("id") ) {
//
//    }

    @GetMapping("/yongeic/question/{id}/statistic")
    public Response getQuestionStatistic(@PathVariable("id") Long questionId) {
        return Response.ofSuccess(dragonLakeService.getQuestionStatistic(questionId));
    }

}
