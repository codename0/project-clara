package de.cleargroup.project.clara.server.servermock;

import de.cleargroup.project.clara.domain.ResponseContainer;
import de.cleargroup.project.clara.domain.ResponseMetadata;
import de.cleargroup.project.clara.domain.survey.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jonas on 28.10.2017.
 */
public class SurveyMockHelper {

    public static ResponseEntity<ResponseContainer> getResponseForSurveyId(String idString){
        try {
            long id = Long.parseLong(idString);
            if(id == 403L){
                return new ResponseEntity(HttpStatus.FORBIDDEN);
            }
            if(id == 42L){
                Map<String,Object> dataMap = new HashMap<>();
                dataMap.put("survey",getSurvey42());
                return new ResponseEntity<>(new ResponseContainer(dataMap), HttpStatus.OK);
            }
        } catch(NumberFormatException e){
            return new ResponseEntity<>(new ResponseContainer(
                    new ResponseMetadata(new String[]{"SurveyId not valid"},null)),HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<>(new ResponseContainer(
                new ResponseMetadata(new String[]{"SurveyId not found"},null)),HttpStatus.NOT_FOUND);
    }

    public static ResponseEntity<ResponseContainer> getResponseForAllSurveys(){
        Map<String,Object> dataMap = new HashMap<>();
        dataMap.put("surveys",new Survey[]{getSurvey42()});
        return new ResponseEntity<>(new ResponseContainer(dataMap), HttpStatus.OK);
    }

    private static Survey[] getAllSurveys(){
        return new Survey[]{getSurvey42()};
    }

    private static Survey getSurvey42() {
        Question[] listOfQuestions = {
                new TextQuestion(1L,"This is a question","",true),
                new TextQuestion(2L,"This is another question","",false),
                new SingleChoiceQuestion(3L,"What is the best language?","",true,Arrays.asList(
                        new ChoiceEntry("CSharp","C#", false),
                        new ChoiceEntry("Java","Java", true),
                        new ChoiceEntry("Asm","Assembler", false)
                )),
                new MultiChoiceQuestion(4L,"Which topics are most interesting?","Technologien / Agile / Communities",true,Arrays.asList(
                        new ChoiceEntry("architektur","Architektur", false),
                        new ChoiceEntry("docker","Docker", true),
                        new ChoiceEntry("java9","Java 9", false),
                        new ChoiceEntry("scrum","Scrum", false)
                ))
        };
        return new Survey(42L,"Survey 42","The magic survey", Arrays.asList(listOfQuestions));
    }
}
