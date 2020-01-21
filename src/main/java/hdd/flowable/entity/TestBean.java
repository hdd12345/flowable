package hdd.flowable.entity;

import org.hibernate.annotations.Generated;

import javax.persistence.*;

@Entity
@Table(name = "test")
public class TestBean {
    @Id
    @GeneratedValue
    private String id;

    private String testColumn;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Column(name = "test_column",length = 255)
    public String getTestColumn() {
        return testColumn;
    }

    public void setTestColumn(String testColumn) {
        this.testColumn = testColumn;
    }
}
