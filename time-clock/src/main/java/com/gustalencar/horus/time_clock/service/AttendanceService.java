package com.gustalencar.horus.time_clock.service;

import models.requests.CreateAttendanceHorusRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.TopicPartition;
import org.springframework.stereotype.Service;

@Service
public class AttendanceService {

    @KafkaListener(topicPartitions = @TopicPartition(topic = "time-clock-processed", partitions = { "0" }), containerFactory = "createAttendanceHorusRequestConcurrentKafkaListenerContainerFactory")
    public void attendanceListener(CreateAttendanceHorusRequest request) {
        System.out.println("Message received consumer: " + request.userId());
    }
}
