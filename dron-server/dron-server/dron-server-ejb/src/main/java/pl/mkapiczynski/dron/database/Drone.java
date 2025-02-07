package pl.mkapiczynski.dron.database;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * Bezzałogowy statek latający - dron - w rzeczywistości instancja aplikacji DronTracker
 * 
 * @author Michal Kapiczynski
 *
 */
@Entity
@Table
public class Drone {

	@Id
	@GeneratedValue
	@Column(name = "drone_id")
	private Long droneId;

	@Column(name = "name")
	private String droneName;

	@Column(name = "description")
	private String droneDescription;

	@Column(name = "cameraAngle")
	private Integer cameraAngle;

	@Enumerated(EnumType.STRING)
	private DroneStatusEnum status;

	@OneToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	@JoinColumn(name = "lastLocation_id")
	private Location lastLocation;

	@ManyToMany(mappedBy = "assignedDrones")
	private List<ClientUser> assignedUsers;

	@OneToMany(mappedBy = "drone", cascade = CascadeType.ALL)
	private List<DroneSession> sessions;

	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "activeSession")
	private DroneSession activeSession;

	public Long getDroneId() {
		return droneId;
	}

	public void setDroneId(Long droneId) {
		this.droneId = droneId;
	}

	public String getDroneName() {
		return droneName;
	}

	public void setDroneName(String droneName) {
		this.droneName = droneName;
	}

	public String getDroneDescription() {
		return droneDescription;
	}

	public void setDroneDescription(String droneDescription) {
		this.droneDescription = droneDescription;
	}

	public Integer getCameraAngle() {
		return cameraAngle;
	}

	public void setCameraAngle(Integer cameraAngle) {
		this.cameraAngle = cameraAngle;
	}

	public DroneStatusEnum getStatus() {
		return status;
	}

	public void setStatus(DroneStatusEnum status) {
		this.status = status;
	}

	public List<DroneSession> getSessions() {
		return sessions;
	}

	public void setSessions(List<DroneSession> sessions) {
		this.sessions = sessions;
	}

	public Location getLastLocation() {
		return lastLocation;
	}

	public void setLastLocation(Location lastLocation) {
		this.lastLocation = lastLocation;
	}

	public List<ClientUser> getAssignedUsers() {
		return assignedUsers;
	}

	public void setAssignedUsers(List<ClientUser> assignedUsers) {
		this.assignedUsers = assignedUsers;
	}

	public DroneSession getActiveSession() {
		return activeSession;
	}

	public void setActiveSession(DroneSession activeSession) {
		this.activeSession = activeSession;
	}

}
