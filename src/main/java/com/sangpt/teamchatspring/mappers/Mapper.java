package com.sangpt.teamchatspring.mappers;

public interface Mapper<Entity, Request, Response> {
    Entity requestToEntity(Request request);

    Response entityToResponse(Entity entity);
}
