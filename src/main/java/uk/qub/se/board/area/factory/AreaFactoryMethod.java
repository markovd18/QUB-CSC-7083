package uk.qub.se.board.area.factory;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import uk.qub.se.board.area.Area;

@FunctionalInterface
public interface AreaFactoryMethod {

    Area construct(String json, ObjectMapper mapper) throws JsonProcessingException;
}
