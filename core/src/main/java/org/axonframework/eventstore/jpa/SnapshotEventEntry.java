/*
 * Copyright (c) 2010-2016. Axon Framework
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *     http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.axonframework.eventstore.jpa;

import org.axonframework.eventsourcing.DomainEventMessage;
import org.axonframework.eventstore.AbstractLegacyDomainEventEntry;
import org.axonframework.serializer.Serializer;

import javax.persistence.Entity;

/**
 * @author Rene de Waele
 */
@Entity
public class SnapshotEventEntry extends AbstractLegacyDomainEventEntry<byte[]> {

    public SnapshotEventEntry(DomainEventMessage<?> eventMessage, Serializer serializer) {
        super(eventMessage, serializer);
    }

    public SnapshotEventEntry(String type, String aggregateIdentifier, long sequenceNumber, String eventIdentifier,
                              Object timeStamp, String payloadType, String payloadRevision, byte[] payload,
                              byte[] metaData) {
        super(type, aggregateIdentifier, sequenceNumber, eventIdentifier, timeStamp, payloadType, payloadRevision,
              payload, metaData);
    }

    protected SnapshotEventEntry() {
    }

    @Override
    protected Class<byte[]> getContentType() {
        return byte[].class;
    }
}
