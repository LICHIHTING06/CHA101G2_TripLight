package com.tw.ai.controller;

import com.tw.ai.service.AiService;
import com.tw.member.model.Member;
import jakarta.servlet.http.HttpSession;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class AiFavoriteController {
    private final AiService aiService;

    @Autowired
    public AiFavoriteController(AiService theaiService) {
        aiService = theaiService;
    }

    @GetMapping("/aiFavorite")
    public ResponseEntity<Object> getAiFavorite(HttpSession session) {
        var user = (Member)session.getAttribute("user");
        var aiFavoriteFromMemberId = aiService.findAIFavoriteFromMemberId(user.getMemberId());
        if (aiFavoriteFromMemberId.isEmpty()) {
            String errorMessage = "No AI favorites found for memberId: " + user.getMemberId();
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorMessage);
        } else {
            return ResponseEntity.ok(aiFavoriteFromMemberId);
        }
    }

    // 刪除資料 TODO
//    @PostMapping ("/aiFavorite/delete")
//    public ResponseEntity<Boolean> deleteData(@RequestParam("aiFavoriteId") int aiFavoriteId) {
//        try {
//            boolean delete = aiService.delete(aiFavoriteId);
//            System.out.println("刪除成功");
//            return ResponseEntity.ok(delete);
//        } catch (Exception e) {
//            // 处理异常情况
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
//        }
//    }



    // 存入資料庫
    @PostMapping("/aiFavorite/{memberId}")
    public ResponseEntity<Boolean> processResultData(@RequestParam("resultData") String resultData, @RequestParam("resultUrl") String resultUrl, @PathVariable("memberId") String memberId, HttpSession session) {
        try {
            var user = (Member)session.getAttribute("user");
            boolean saved = aiService.save(resultData, resultUrl, memberId,user.getMemberId());
            return ResponseEntity.ok(saved);
        } catch (Exception e) {
            // 處理異常狀況
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }
    }


}
