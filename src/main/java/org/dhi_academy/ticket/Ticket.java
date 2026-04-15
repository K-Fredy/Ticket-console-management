package org.dhi_academy.ticket;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Getter
@ToString
@EqualsAndHashCode(of = {"id"})

public class Ticket {

    private final String id;
    private final String title;
    private final String description;
    private final Priority priority;
    private final LocalDateTime openedAt;
    private final LocalDateTime occurredAt;
    private final String requester;
    private final String department;

    private LocalDateTime createdAt;
    private String assignedTo;
    private LocalDateTime assignedAt;
    private LocalDateTime updatedAt;
    private LocalDateTime closedAt;
    private LocalDateTime resolvedAt;
    private TicketStatus status;


    public Ticket(String id, String title, String description, Priority priority, LocalDateTime openedAt, LocalDateTime occurredAt, String requester, String department, LocalDateTime createdAt, String assignedTo, LocalDateTime assignedAt, LocalDateTime updatedAt, LocalDateTime closedAt, LocalDateTime resolvedAt, TicketStatus status) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.priority = priority;
        this.openedAt = openedAt;
        this.occurredAt = occurredAt;
        this.requester = requester;
        this.department = department;
        this.createdAt = createdAt;
        this.assignedTo = assignedTo;
        this.assignedAt = assignedAt;
        this.updatedAt = updatedAt;
        this.closedAt = closedAt;
        this.resolvedAt = resolvedAt;
        this.status = status;
    }

    public static Ticket openTicket(String title, String description, Priority priority, String requester, String department, LocalDateTime occurredAt) {

        if (occurredAt == null) {
            throw new IllegalArgumentException("the given occurredAt is null");
        } else if (occurredAt.isAfter(LocalDateTime.now())) {
            throw new IllegalArgumentException("the give occurredAt is after the given date");
        } else if (title != null && !title.trim().isEmpty()) {
            if (description != null && !description.trim().isEmpty()) {
                if (requester != null && !requester.trim().isEmpty()) {
                    if (department != null && !department.trim().isEmpty()) {
                        return new Ticket(UUID.randomUUID().toString(), title, description, priority == null ? Priority.LOW : priority, LocalDateTime.now(), occurredAt, requester, department, LocalDateTime.now(), (String) null, (LocalDateTime) null, (LocalDateTime) null, (LocalDateTime) null, (LocalDateTime) null, TicketStatus.OPEN);
                    } else {
                        throw new IllegalArgumentException("the given department is null or empty");
                    }
                } else {
                    throw new IllegalArgumentException("the given requester is null or empty");
                }
            } else {
                throw new IllegalArgumentException("the given description is null or empty");
            }

        } else {
            throw new IllegalArgumentException("the given title is null or empty");
        }
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ticket ticket = (Ticket) o;
        return Objects.equals(id, ticket.id);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(id);
    }

    @Override
    public String toString() {
        return "\nTicket{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", priority=" + priority +
                ", openedAt=" + openedAt +
                ", occurredAt=" + occurredAt +
                ", requester='" + requester + '\'' +
                ", department='" + department + '\'' +
                ", createdAt=" + createdAt +
                ", assignedTo='" + assignedTo + '\'' +
                ", assignedAt=" + assignedAt +
                ", updatedAt=" + updatedAt +
                ", closedAt=" + closedAt +
                ", resolvedAt=" + resolvedAt +
                ", status=" + status +
                "}";
    }
}
