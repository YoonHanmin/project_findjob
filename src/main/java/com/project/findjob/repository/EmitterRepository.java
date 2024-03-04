package com.project.findjob.repository;

import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.util.Map;

public interface EmitterRepository  {

    SseEmitter save(String emitterId, SseEmitter sseEmitter); //Emitter 저장
    void saveEventCache(String eventCacheId, Object event); //이벤트 저장
    Map<String, SseEmitter> findAllEmitterStartWithById(String id); //해당 사용자와 관련된 모든 Emitter를 찾음
    Map<String, Object> findAllEventCacheStartWithById(String id); //해당 사용자와 관련된 모든 이벤트를 찾음
    void deleteById(String id); //해당 id의 Emitter를 지운다
    void deleteAllEmitterStartWithId(String id); //해당 회원과 관련된 모든 Emitter를 지움
    void deleteAllEventCacheStartWithId(String id); //해당 회원과 관련된 모든 이벤트를 지움
}
