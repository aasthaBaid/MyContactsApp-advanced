
package com.tag;

public class Tag {

    private final String name;

    public Tag(String name) {
        if(name == null || name.trim().isEmpty())
            throw new IllegalArgumentException("Tag name cannot be empty");

        this.name = name.trim().toLowerCase(); // normalize for uniqueness
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if(this == o) return true;
        if(!(o instanceof Tag)) return false;
        Tag other = (Tag) o;
        return name.equals(other.name);
    }

    @Override
    public int hashCode() {
        return name.hashCode();
    }

    @Override
    public String toString() {
        return "#" + name;
    }
}
