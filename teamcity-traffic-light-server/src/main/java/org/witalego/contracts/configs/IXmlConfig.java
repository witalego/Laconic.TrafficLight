package org.witalego.contracts.configs;

import org.jdom.Element;

public interface IXmlConfig
{
    Boolean isChanged();
    void resetIsChanged();

    void load(Element element);
    void save(Element element);
}