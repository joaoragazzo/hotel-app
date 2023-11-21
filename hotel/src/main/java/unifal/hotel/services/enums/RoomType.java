package unifal.hotel.services.enums;

public enum RoomType
{
    SUITE(1),
    NORMAL(2),
    DOUBLE(3);

    private final int value;

    RoomType(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }

}
