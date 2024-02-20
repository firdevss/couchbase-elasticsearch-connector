/*
 * Copyright 2018 Couchbase, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.couchbase.connector.dcp;

import com.fasterxml.jackson.annotation.JsonProperty;

import static java.util.Objects.requireNonNull;

/**
 * Position in a DCP stream.
 */
public class Checkpoint {
    private final long vbuuid; // VBucket UUID for which the sequence number is valid
    private final long seqno; // Sequence number of a DCP event
    private final SnapshotMarker snapshot;

    public static final Checkpoint ZERO = new Checkpoint(0, 0, new SnapshotMarker(0, 0));

    public Checkpoint(@JsonProperty("vbuuid") long vbuuid,
                      @JsonProperty("seqno") long seqno,
                      @JsonProperty("snapshot") SnapshotMarker snapshot) {
        this.vbuuid = vbuuid;
        this.seqno = seqno;
        this.snapshot = Objects.requireNonNull(snapshot);
    }

    public long getVbuuid() {
        return vbuuid;
    }

    public long getSeqno() {
        return seqno;
    }

    public SnapshotMarker getSnapshot() {
        return snapshot;
    }

    @Override
    public String toString() {
        return vbuuid + "@" + seqno + snapshot;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Checkpoint)) return false;
        Checkpoint that = (Checkpoint) o;
        return vbuuid == that.vbuuid && seqno == that.seqno && snapshot.equals(that.snapshot);
    }

    @Override
    public int hashCode() {
        return Objects.hash(vbuuid, seqno, snapshot);
    }
}
