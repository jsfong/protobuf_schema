package com.protobuf.demo.api;

import com.google.protobuf.InvalidProtocolBufferException;
import com.google.protobuf.Value;
import com.google.protobuf.util.JsonFormat;
import com.protobuf.demo.CubsObjectProto;
import com.protobuf.demo.CubsObjectProto.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class Test {

    @GetMapping("/protobuf")
    public ResponseEntity<String> getProtoBuf() throws InvalidProtocolBufferException {
        log.info("Get Proto buf");

        //Create proto
        var valueBuilder = Value.newBuilder();
        valueBuilder.setStringValue("Sample payload");


        var cubsObject = CubsObject.newBuilder()
                .setId("cd4ad31a-bcde-460d-8532-6599ceb77be2")
                .setModelId("906523c4-66c6-4674-9d54-91b3cf1c9df7")
                .setName("sample name")
                .setPayload(valueBuilder)
                .build();



        //Proto --> Json
        var jCubsObject = JsonFormat.printer().print(cubsObject);

        return new ResponseEntity<>(jCubsObject, HttpStatus.CREATED);

    }

    @PostMapping(value = "/protobuf")
    public ResponseEntity<String> addProto(@RequestBody String jCubsObject) throws InvalidProtocolBufferException {

        //JSON --> Proto
        var builder = CubsObject.newBuilder();
        JsonFormat.parser().merge(jCubsObject, builder);
        var cubsObjectProto = builder.build();

        //Proto --> JSON
        var newJCubsObject = JsonFormat.printer().print(cubsObjectProto);
        log.info("Receive proto {}", newJCubsObject);

        return new ResponseEntity(newJCubsObject, HttpStatus.CREATED);
    }

}
