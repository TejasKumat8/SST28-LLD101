public class ClassroomController {
    private final DeviceRegistry reg;

    public ClassroomController(DeviceRegistry reg) {
        this.reg = reg;
    }

    public void startClass() {
        Object pjObj = reg.getFirstOfType("Projector");
        ((PowerControl) pjObj).powerOn();
        ((InputConnection) pjObj).connectInput("HDMI-1");

        Object lightsObj = reg.getFirstOfType("LightsPanel");
        ((BrightnessControl) lightsObj).setBrightness(60);

        Object acObj = reg.getFirstOfType("AirConditioner");
        ((TemperatureControl) acObj).setTemperatureC(24);

        Object scanObj = reg.getFirstOfType("AttendanceScanner");
        System.out.println("Attendance scanned: present=" + ((Scanner) scanObj).scanAttendance());
    }

    public void endClass() {
        System.out.println("Shutdown sequence:");
        ((PowerControl) reg.getFirstOfType("Projector")).powerOff();
        ((PowerControl) reg.getFirstOfType("LightsPanel")).powerOff();
        ((PowerControl) reg.getFirstOfType("AirConditioner")).powerOff();
    }
}
