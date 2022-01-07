package cn.original.ide.launch.layout;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.InputStream;

import oms.ability.ModAbility;
import oms.io.File;


public class XmlToViewTree extends ModAbility {
    private StringBuilder viewTreeClass = new StringBuilder();
    private StringBuilder viewTreeVal = new StringBuilder();
    private XmlData data;

    public XmlToViewTree(XmlData xmlData) {
        String string = Name2Name(xmlData.name);
        this.data = xmlData;
        viewTreeClass.append("package ");
        viewTreeClass.append(xmlData.packageName + ".viewtree");
        viewTreeClass.append(";\n");
        viewTreeClass.append("import static ");
        viewTreeClass.append(xmlData.packageName);
        viewTreeClass.append(".R.*;");
        viewTreeClass.append("public class ");
        viewTreeClass.append(string);
        viewTreeClass.append("{\n");
        viewTreeClass.append("public ");
        viewTreeClass.append(string);
        viewTreeClass.append("(android.app.Activity activity){\n" +
                "super(activity);\n" +
                "}\n");
        viewTreeClass.append("(android.app.Activity activity,boolean fal){\n" +
                "super(activity,fal);\n" +
                "}\n");
        viewTreeClass.append("@Override\n" +
                "    public int getLayoutID() {\n" +
                "        return layout." + xmlData.name +
                ";\n }\n");
        viewTreeClass.append("@Override\n" +
                "    public void init() {\n");
    }

    @Override
    public String toString() {
        try {
            filterXml(new File(data.path).getInputStream());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        return viewTreeClass.toString();
    }


    public static String Name2Name(String a) {
        StringBuilder builder = new StringBuilder();
        char[] chars = a.toCharArray();
        boolean state = false;
        for (int t = 0; t < chars.length; t++) {
            char ca = chars[t];
            if (t == 0) {
                if (ca == '_') {
                    continue;
                }
                builder.append(String.valueOf(ca).toUpperCase());
                state = false;
                continue;
            } else {
                if (ca == '_') {
                    state = true;
                } else if (state) {
                    state = false;
                    builder.append(String.valueOf(ca).toUpperCase());
                } else {
                    builder.append(ca);
                }
            }
        }
        return builder.toString();
    }

    public void filterXml(InputStream xml) throws Exception {
        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        // 获得xml解析类的引用
        XmlPullParser parser = factory.newPullParser();
        parser.setInput(xml, "UTF-8");
        // 获得事件的类型
        int eventType = parser.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            switch (eventType) {
                case XmlPullParser.START_TAG:
                    String name = parser.getName();
                    if (name.trim() == "include") {
                        continue;
                    }
                    viewTreeVal.append("public ");
                    if (name.equals(".")) {
                        viewTreeVal.append(name);
                        viewTreeVal.append(" ");
                    } else if (name.trim() == "WebView") {
                        viewTreeVal.append("android.webkit.");
                        viewTreeVal.append(name);
                        viewTreeVal.append(" ");
                    } else {
                        viewTreeVal.append("android.widget");
                        viewTreeVal.append(name);
                        viewTreeVal.append(" ");
                    }
                    for (int t = 0; t < parser.getAttributeCount(); t++) {
                        if ("android:id".equals(parser.getAttributeName(t)) || "android:id".contains(parser.getAttributeName(t))) {
                            String value = parser.getAttributeValue(t).replace("@+id/", "");
                            viewTreeVal.append(value);
                            viewTreeVal.append(";\n");
                            viewTreeClass.append(value);
                            viewTreeClass.append("=findViewById(id.");
                            viewTreeClass.append(value);
                            viewTreeClass.append(");");
                            viewTreeClass.append("\n");
                        }
                    }
                    break;
            }
            eventType = parser.next();
        }
        viewTreeClass.append("}\n");
        viewTreeClass.append(viewTreeVal);
        viewTreeClass.append("}\n");
    }


}
