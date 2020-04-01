package psu.ajm6684.myapplication;

public class Teams {

    private String TeamName;
    private String Guard;
    private String ForwardGuard;
    private String GuardForward;
    private String CenterForward;
    private String Center;

    public String getTeamName() {
        return TeamName;
    }

    public void setTeamName(String teamName) {
        TeamName = teamName;
    }

    public String getGuard() {
        return Guard;
    }

    public void setGuard(String guard) {
        Guard = guard;
    }

    public String getForwardGuard() {
        return ForwardGuard;
    }

    public void setForwardGuard(String forwardGuard) {
        ForwardGuard = forwardGuard;
    }

    public String getGuardForward() {
        return GuardForward;
    }

    public void setGuardForward(String guardForward) {
        GuardForward = guardForward;
    }

    public String getCenterForward() {
        return CenterForward;
    }

    public void setCenterForward(String centerForward) {
        CenterForward = centerForward;
    }

    public String getCenter() {
        return Center;
    }

    public void setCenter(String center) {
        Center = center;
    }

    public Teams(String teamName, String guard, String forwardGuard, String guardForward, String centerForward, String center) {
        TeamName = teamName;
        Guard = guard;
        ForwardGuard = forwardGuard;
        GuardForward = guardForward;
        CenterForward = centerForward;
        Center = center;
    }

    public Teams() {}


}
