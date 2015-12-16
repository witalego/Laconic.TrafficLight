package org.witalego.config;

import org.jdom.Attribute;
import org.jdom.Element;
import org.witalego.contracts.args.Action;
import org.witalego.contracts.args.Func1;
import org.witalego.contracts.configs.IXmlConfig;
import org.witalego.contracts.logging.ILog;
import org.witalego.contracts.track.ITrackable;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseConfig implements IXmlConfig
{
    private Boolean _isChanged = false;

    private final List<ITrackable> _trackables = new ArrayList<>();

    private ILog _log;

    /*public BaseConfig(ILog log)
    {
        _log = log;
    }*/

    protected void init(ITrackable... trackables)
    {
        //_log.info("BaseConfig.init");
        for (ITrackable trackable : trackables)
        {
            //_log.info("BaseConfig.init: %s", trackable.getClass().getName());
            _trackables.add(trackable);
        }
    }

    @Override
    public Boolean isChanged()
    {
        //_log.info("BaseConfig.isChanged");

        Boolean isChanged = false;

        for (ITrackable trackable : _trackables)
        {
            isChanged = isChanged || trackable.isChanged();
        }

        return isChanged;
    }

    @Override
    public void resetIsChanged()
    {
        _trackables.forEach(ITrackable::save);
    }

    protected <T> void updateField(T oldValue, T newValue, Action<T> setAction)
    {
        if (newValue.equals(oldValue))
        {
            return;
        }

        setAction.invoke(newValue);
        _isChanged = true;
    }

    protected void getStringAttribute(Element root, String name, Action<String> setFunc)
    {
        getAttribute(
            root,
            name,
            setFunc,
            value -> value);
    }

    protected void getIntegerAttribute(Element root, String name, Action<Integer> setFunc)
    {
        getAttribute(
            root,
            name,
            setFunc,
            Integer::parseInt);
    }

    protected void getBooleanAttribute(Element root, String name, Action<Boolean> setFunc)
    {
        getAttribute(
            root,
            name,
            setFunc,
            Boolean::parseBoolean);
    }

    protected <T> void getAttribute(Element root, String name, Action<T> setFunc, Func1<String, T> convertFunc)
    {
        if (root == null)
        {
            return;
        }

        Attribute attribute = root.getAttribute(name);
        if (attribute != null)
        {
            setFunc.invoke(convertFunc.invoke(attribute.getValue()));
        }
    }

    protected void getStringElement(Element root, String name, Action<String> setFunc)
    {
        getElement(
            root,
            name,
            setFunc,
            value -> value);
    }

    protected void getIntegerElement(Element root, String name, Action<Integer> setFunc)
    {
        getElement(
            root,
            name,
            setFunc,
            Integer::parseInt);
    }

    protected <T> void getElement(Element root, String name, Action<T> setFunc, Func1<String, T> convertFunc)
    {
        if (root == null)
        {
            return;
        }

        Element element = root.getChild(name);
        if (element != null)
        {
            setFunc.invoke(convertFunc.invoke(element.getText()));
        }
    }

    protected <T> void addElement(Element root, String name, Action<T> setFunc, Func1<String, T> convertFunc)
    {
        Element element = root.getChild(name);
        if (element == null)
        {
            element = new Element(name);
            root.addContent(element);
        }
    }

    protected Element getElement(Element root, String name)
    {
        Element element = root.getChild(name);
        if (element == null)
        {
            element = new Element(name);
            root.addContent(element);
        }

        return element;
    }

    protected Element setElement(Element root, String name, Object value)
    {
        Element element = getElement(root, name);

        element.setText(value.toString());

        return element;
    }
}