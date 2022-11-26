module com.example.basejavafxhibernate {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.sql;
    requires lombok;
    requires java.persistence;
    requires org.hibernate.orm.core;
    requires java.naming;

    opens com.example.basejavafxhibernate to javafx.fxml, org.hibernate.orm.core;
    exports com.example.basejavafxhibernate;
}