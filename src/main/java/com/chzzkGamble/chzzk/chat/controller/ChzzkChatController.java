package com.chzzkGamble.chzzk.chat.controller;

import com.chzzkGamble.chzzk.api.ChzzkApiService;
import com.chzzkGamble.chzzk.chat.dto.ChatConnectRequest;
import com.chzzkGamble.chzzk.chat.service.ChzzkChatService;
import com.chzzkGamble.chzzk.dto.ChannelInfoApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class ChzzkChatController {

    private final ChzzkChatService chzzkChatService;
    private final ChzzkApiService chzzkApiService;

    @PostMapping("/connect")
    public ResponseEntity<ChannelInfoApiResponse> connect(@RequestBody ChatConnectRequest request) {
        String channelName = request.getChannelName();
        chzzkChatService.connectChatRoom(channelName);
        ChannelInfoApiResponse response = chzzkApiService.getChannelInfo(channelName);

        return ResponseEntity.ok(response);
    }
}