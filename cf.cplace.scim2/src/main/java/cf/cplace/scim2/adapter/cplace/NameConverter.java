package cf.cplace.scim2.adapter.cplace;

import com.unboundid.scim2.common.types.Name;

import java.util.Arrays;

import static org.apache.commons.lang3.StringUtils.isBlank;
import static org.apache.commons.lang3.StringUtils.isNotBlank;

class NameConverter {
    static String toCplaceName(final Name name) {
        if (name == null) {
            return "";
        }
        final StringBuilder cplaceName = new StringBuilder();
        if (isNotBlank(name.getGivenName())) {
            cplaceName.append(name.getGivenName());
        }
        if (isNotBlank(name.getMiddleName())) {
            if (cplaceName.length() > 0) {
                cplaceName.append(" ");
            }
            cplaceName.append(name.getMiddleName());
        }
        if (isNotBlank(name.getFamilyName())) {
            if (cplaceName.length() > 0) {
                cplaceName.append(" ");
            }
            cplaceName.append(name.getFamilyName());
        }
        return cplaceName.toString();
    }

    static Name toScimName(final String cplaceName) {
        if (isBlank(cplaceName)) {
            return new Name();
        }
        final String[] nameElements = cplaceName.split(" ");
        if (nameElements.length == 1) {
            return new Name().setFamilyName(nameElements[0]);
        }
        if (nameElements.length == 2) {
            return new Name().setGivenName(nameElements[0]).setFamilyName(nameElements[1]);
        }
        return new Name()
                .setGivenName(nameElements[0])
                .setMiddleName(String.join(" ", Arrays.copyOfRange(nameElements, 1, nameElements.length - 1)))
                .setFamilyName(nameElements[nameElements.length -1]);
    }
}
