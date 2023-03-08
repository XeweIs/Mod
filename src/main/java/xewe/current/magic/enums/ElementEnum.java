package xewe.current.magic.enums;

public enum ElementEnum {
    Fire("Fire"), Air("Air"), Earth("Earth"), Water("Earth"), None("None");

    private final String element;

    ElementEnum(String element) {
        this.element = element;
    }


    @Override
    public String toString() {
        return element;
    }
}
