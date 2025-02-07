package pl.mkapiczynski.dron.database;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Sesja drona
 * 
 * @author Michal Kapiczynski
 *
 */
@Entity
@Table(name = "DroneSession")
public class DroneSession {
	@Id
	@GeneratedValue
	@Column(name = "session_id")
	private Long sessionId;

	@ManyToOne(optional = false, cascade = CascadeType.ALL)
	@JoinColumn(name = "drone_id", nullable = false)
	private Drone drone;

	@Column(name = "started")
	private Date sessionStarted;

	@Column(name = "ended")
	private Date sessionEnded;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "searchedArea")
	private SearchedArea searchedArea;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "lastSearchedArea")
	private SearchedArea lastSearchedArea;

	@Enumerated(EnumType.STRING)
	private DroneSessionStatusEnum status;

	public Long getSessionId() {
		return sessionId;
	}

	public void setSessionId(Long sessionId) {
		this.sessionId = sessionId;
	}

	public Drone getDrone() {
		return drone;
	}

	public void setDrone(Drone drone) {
		this.drone = drone;
	}

	public Date getSessionStarted() {
		return sessionStarted;
	}

	public void setSessionStarted(Date sessionStarted) {
		this.sessionStarted = sessionStarted;
	}

	public Date getSessionEnded() {
		return sessionEnded;
	}

	public void setSessionEnded(Date sessionEnded) {
		this.sessionEnded = sessionEnded;
	}

	public SearchedArea getSearchedArea() {
		return searchedArea;
	}

	public void setSearchedArea(SearchedArea searchedArea) {
		this.searchedArea = searchedArea;
	}

	public SearchedArea getLastSearchedArea() {
		return lastSearchedArea;
	}

	public void setLastSearchedArea(SearchedArea lastSearchedArea) {
		this.lastSearchedArea = lastSearchedArea;
	}

	public DroneSessionStatusEnum getStatus() {
		return status;
	}

	public void setStatus(DroneSessionStatusEnum status) {
		this.status = status;
	}

}
