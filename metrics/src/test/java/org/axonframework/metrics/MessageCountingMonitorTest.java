package org.axonframework.metrics;

import com.codahale.metrics.Counter;
import com.codahale.metrics.Metric;
import org.axonframework.eventhandling.EventMessage;
import org.axonframework.messaging.Message;
import org.axonframework.monitoring.MessageMonitor;
import org.junit.jupiter.api.*;

import java.util.Arrays;
import java.util.Map;

import static org.axonframework.eventhandling.GenericEventMessage.asEventMessage;
import static org.junit.jupiter.api.Assertions.*;

class MessageCountingMonitorTest {

    @Test
    void testMessages(){
        MessageCountingMonitor testSubject = new MessageCountingMonitor();
        EventMessage<Object> foo = asEventMessage("foo");
        EventMessage<Object> bar = asEventMessage("bar");
        EventMessage<Object> baz = asEventMessage("baz");
        Map<? super Message<?>, MessageMonitor.MonitorCallback> callbacks = testSubject.onMessagesIngested(Arrays.asList(foo, bar, baz));
        callbacks.get(foo).reportSuccess();
        callbacks.get(bar).reportFailure(null);
        callbacks.get(baz).reportIgnored();

        Map<String, Metric> metricSet = testSubject.getMetrics();

        Counter ingestedCounter = (Counter) metricSet.get("ingestedCounter");
        Counter processedCounter = (Counter) metricSet.get("processedCounter");
        Counter successCounter = (Counter) metricSet.get("successCounter");
        Counter failureCounter = (Counter) metricSet.get("failureCounter");
        Counter ignoredCounter = (Counter) metricSet.get("ignoredCounter");

        assertEquals(3, ingestedCounter.getCount());
        assertEquals(2, processedCounter.getCount());
        assertEquals(1, successCounter.getCount());
        assertEquals(1, failureCounter.getCount());
        assertEquals(1, ignoredCounter.getCount());
    }
}