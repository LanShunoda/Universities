package com.plorial.universities;

import java.util.ArrayList;

/**
 * Created by plorial on 09.02.16.
 */
public enum UniversitiesTable {
    TABLE_NAME("UNIVERSITIES"),
    NAME("NAME"),
    CITY("CITY"),
    YEAR("YEAR"),
    STATUS("STATUS"),
    ACCREDITATION("ACCREDITATION"),
    DOCUMENT("DOCUMENT"),
    FORM_OF_EDUCATION("FORM_OF_EDUCATION"),
    QUALIFICATION("QUALIFICATION"),
    ADDRESS("ADDRESS"),
    TELEPHONE("TELEPHONE"),
    TELEPHONE_OF_SELECTION_COMMITTEE("TELEPHONE_OF_SELECTION_COMMITTEE"),
    SITE("SITE"),
    URL("URL"),
    TRAINING_AREAS("TRAINING_AREAS"),
    FACULTIES("FACULTIES");


    private final String text;

    /**
     * @param text
     */
    private UniversitiesTable(final String text) {
        this.text = text;
    }

    /* (non-Javadoc)
     * @see java.lang.Enum#toString()
     */
    @Override
    public String toString() {
        return text;
    }

    public String[] getTableColumns(){
        ArrayList<String> tableColumnsList = new ArrayList<>();
        for (UniversitiesTable e : UniversitiesTable.values()){
            if(!e.equals(UniversitiesTable.TABLE_NAME))
                tableColumnsList.add(e.toString());
        }
        String[] result = new String[tableColumnsList.size()];
        result = tableColumnsList.toArray(result);
        return result;
    }
}
