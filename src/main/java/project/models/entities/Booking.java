package project.models.entities;

import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import project.models.BookingStatus;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Booking {
	
	@Id @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Column(length = 16, columnDefinition = "varchar(16) default 'PENDING'")
	@Enumerated(value = EnumType.STRING)
	private BookingStatus status = BookingStatus.PENDING;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime begin;
	
	@JsonFormat(pattern = "dd/MM/yyyy HH:mm")
	private LocalDateTime end;
	
	private int weekRepetition;
	
	private double price;
	
	@JsonIgnore
	@ManyToOne
	private User client;
	
	@JsonIgnore
	@ManyToOne
	private Room room;
	
	public Set<LocalDate> getDates() {
		Set<LocalDate> dates = new HashSet<>();

		// si la reservation ne concerne qu'une journée
		if (weekRepetition == 0 || begin.toLocalDate().equals(end.toLocalDate())) {
			dates.add(begin.toLocalDate());

			// sinon générer toutes les dates en ajoutant 1 jour à la fin car exclusive
		} else {
			dates = begin.toLocalDate().datesUntil(end.toLocalDate().plusDays(1), Period.ofWeeks(weekRepetition)).collect(Collectors.toSet());
		}
		return dates;
	}
	
	public void calculatePrice() {
		double p = getDates().size() * getDuration() * getRoom().getPrice();
		p += p / 10;
		this.price = p;
	}
	
	private int getDuration() {
		return (int) Duration.between(begin.toLocalTime(), end.toLocalTime()).toHours();
	}
}
