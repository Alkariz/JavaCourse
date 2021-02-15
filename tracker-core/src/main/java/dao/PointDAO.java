package dao;

import javax.persistence.*;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name="Points")
public class PointDAO {

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "ID")
    private long id;

    @Column(name = "LAT")
    private double lat;
    @Column(name = "LON")
    private double lon;
    @Column(name = "TIME")
    private long time;
    @Column(name = "ELE")
    private double ele; // Высота

    public double getLat() {
        return lat;
    }

    public void setLat(double lat) {
        this.lat = lat;
    }

    public double getLon() {
        return lon;
    }

    public void setLon(double lon) {
        this.lon = lon;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getEle() {
        return ele;
    }

    public void setEle(double ele) {
        this.ele = ele;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "{" +
                "id: " + id +
                ", lat: " + lat +
                ", lon: " + lon +
                ", ele: " + ele +
                ", time: " + time +
                '}';
    }

    @Override
    public int hashCode() {
        return toString().hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        if (!obj.getClass().equals(PointDAO.class))
        {
            return false;
        }
        return (this.hashCode() == obj.hashCode());
    }
}
