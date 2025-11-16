module ca.qc.bdeb.sim.prjtp2_aut25 {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.sql;
    requires java.desktop;


    opens ca.qc.bdeb.sim.prjtp2_aut25 to javafx.fxml;
    exports ca.qc.bdeb.sim.prjtp2_aut25;
    exports ca.qc.bdeb.sim.prjtp2_aut25.Maison;
    opens ca.qc.bdeb.sim.prjtp2_aut25.Maison to javafx.fxml;
}