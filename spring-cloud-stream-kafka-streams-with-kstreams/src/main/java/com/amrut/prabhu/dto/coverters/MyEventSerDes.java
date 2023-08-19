package com.amrut.prabhu.dto.coverters;

import com.amrut.prabhu.dto.MyEvent;
import org.springframework.kafka.support.serializer.JsonSerde;

public class MyEventSerDes extends JsonSerde<MyEvent> {

}
