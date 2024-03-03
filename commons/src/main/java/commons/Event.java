package commons;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
public class Event {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    public long id;
    public String title;
    //@OneToMany(cascade = CascadeType.ALL)
    @Column(name = "PARTICIPANTS?")
    @ElementCollection//(targetClass = List.class)
    public List<Long> participants;
    //@OneToMany(cascade = CascadeType.ALL)
    @Column(name = "EXPENSES?")
    @ElementCollection//(targetClass = List.class)
    public List<Long> expenses;

    public Event(){

    }

    public Event(String title){
        this.title = title;
        participants = new ArrayList<>();
        expenses = new ArrayList<>();
    }

    public Event(Long id, String title, List<Long> participants, List<Long> expenses){
        this.id = id;
        this.title = title;
        this.participants = participants;
        this.expenses = expenses;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Long> getParticipants() {
        return participants;
    }

    public void setParticipants(List<Long> participants) {
        this.participants = participants;
    }

    public void addParticipant(Participant participant) {
        this.participants.add(participant.getId());
    }

    public List<Long> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Long> expenses) {
        this.expenses = expenses;
    }

    public void addExpense(Expense expense) {
        this.expenses.add(expense.getId());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Event event)) return false;
        return getId() == event.getId() && Objects.equals(getTitle(), event.getTitle())
                && Objects.equals(getParticipants(), event.getParticipants()) && Objects.equals(getExpenses(), event.getExpenses());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId(), getTitle(), getParticipants(), getExpenses());
    }

    @Override
    public String toString() {
        return title;
    }
}
