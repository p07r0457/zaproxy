/*
 * Zed Attack Proxy (ZAP) and its related class files.
 * 
 * ZAP is an HTTP/HTTPS proxy for assessing web application security.
 * 
 * Copyright 2017 The ZAP Development Team
 *  
 * Licensed under the Apache License, Version 2.0 (the "License"); 
 * you may not use this file except in compliance with the License. 
 * You may obtain a copy of the License at 
 * 
 *   http://www.apache.org/licenses/LICENSE-2.0 
 *   
 * Unless required by applicable law or agreed to in writing, software 
 * distributed under the License is distributed on an "AS IS" BASIS, 
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. 
 * See the License for the specific language governing permissions and 
 * limitations under the License. 
 */
package org.zaproxy.zap.extension.api;

import java.util.ArrayList;
import java.util.List;

import org.parosproxy.paros.Constant;
import org.zaproxy.zap.network.DomainMatcher;
import org.zaproxy.zap.view.AbstractMultipleOptionsTableModel;

public class APIIpAccessTableModel extends AbstractMultipleOptionsTableModel<DomainMatcher> {

    private static final long serialVersionUID = -5411351965957264957L;

    private static final String[] COLUMN_NAMES = {
            Constant.messages.getString("api.options.ipaddr.table.header.enabled"),
            Constant.messages.getString("api.options.ipaddr.table.header.regex"),
            Constant.messages.getString("api.options.ipaddr.table.header.value") };

    private static final int COLUMN_COUNT = COLUMN_NAMES.length;

    private List<DomainMatcher> addresses = new ArrayList<>(5);

    public APIIpAccessTableModel() {
        super();
    }

    @Override
    public String getColumnName(int col) {
        return COLUMN_NAMES[col];
    }

    @Override
    public int getColumnCount() {
        return COLUMN_COUNT;
    }

    @Override
    public int getRowCount() {
        return addresses.size();
    }

    @Override
    public boolean isCellEditable(int rowIndex, int columnIndex) {
        return (columnIndex == 0);
    }

    @Override
    public Object getValueAt(int rowIndex, int columnIndex) {
        switch (columnIndex) {
        case 0:
            return Boolean.valueOf(getElement(rowIndex).isEnabled());
        case 1:
            return Boolean.valueOf(getElement(rowIndex).isRegex());
        case 2:
            return getElement(rowIndex).getValue();
        }
        return null;
    }

    @Override
    public void setValueAt(Object aValue, int rowIndex, int columnIndex) {
        if (columnIndex == 0 && aValue instanceof Boolean) {
            addresses.get(rowIndex).setEnabled(((Boolean) aValue).booleanValue());
            fireTableCellUpdated(rowIndex, columnIndex);
        }
    }

    @Override
    public Class<?> getColumnClass(int c) {
        if (c == 0 || c == 1) {
            return Boolean.class;
        }
        return String.class;
    }

    public List<DomainMatcher> getIpAddresses() {
        return addresses;
    }

    public void setIpAddresses(List<DomainMatcher> addrs) {
        this.addresses = new ArrayList<>(addrs.size());

        for (DomainMatcher addr : addrs) {
            this.addresses.add(new DomainMatcher(addr));
        }

        fireTableDataChanged();
    }

    @Override
    public List<DomainMatcher> getElements() {
        return addresses;
    }
}
