package org.system.i18n.model.dto;

import com.vaadin.flow.component.HasValue;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.orderedlayout.HorizontalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.system.i18n.CountryFlagT;

import java.util.Map;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class MultiLangualTextAreaDTO implements HasValue.ValueChangeEvent<Map<Integer, String>> {

    private HorizontalLayout horizontalLayout;
    private TextArea textArea;
    private Map<Integer, String> data;
    private CountryFlagT flag;

    @Override
    public HasValue<?, Map<Integer, String>> getHasValue() {
        return null;
    }

    @Override
    public boolean isFromClient() {
        return false;
    }

    @Override
    public Map<Integer, String> getOldValue() {
        return this.getData();
    }

    @Override
    public Map<Integer, String> getValue() {
        return this.getData();
    }
}
