module ca.qc.bdeb.sim.prjtp2_aut25 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;


    opens ca.qc.bdeb.sim.prjtp2_aut25 to javafx.fxml;
    exports ca.qc.bdeb.sim.prjtp2_aut25;
}