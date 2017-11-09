package se.sunet.ati.ladok.rest.api.utbildningsinformation.type;

public enum Status {
    UTKAST(1),
    PABORJAD(2),
    KOMPLETT(3),
    AVVECKLAD_INSTALLD(4);

    private final int id;

    Status(int id) {
        this.id = id;
    }

    public static Status fromId(Integer id) {
        for (Status status : values()) {
            if(status.id == id){
                return status;
            }
        }
        return null;
    }

    @Override
    // Needs to override so queryparam gets correct value
    public String toString() {
        return Integer.toString(id);
    }
}
