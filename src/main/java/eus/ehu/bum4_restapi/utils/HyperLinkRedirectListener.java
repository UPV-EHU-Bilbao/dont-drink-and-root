/*
 * This file is part of the BUM4_REST-API project.
 *
 * This program is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License as published by the
 * Free Software Foundation; either version 3 of the License, or (at your
 * option) any later version.
 *
 * This program is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for
 * more details.
 *
 * You should have received a copy of the GNU General Public License along
 * with this program. If not, see <http://www.gnu.org/licenses/>.
 *
 *
 * @author -  MatWein
 * StackOverflow: https://stackoverflow.com/users/5508764/matwein
 */

/*
 * Thanks to MatWein I got this working, this file has been taken from:
 * https://stackoverflow.com/questions/15555510/javafx-stop-opening-url-in-webview-open-in-browser-instead
 *
 * Additionally, if the error "Error:(1, 1) java: module JavaFX reads package java.awt from both
 * java.desktop and java.datatransfer" appers, this will be of help:
 * https://stackoverflow.com/questions/62307840/when-this-error-occurs-error1-1-java-module-javafx-reads-package-java-awt-f
 */

package eus.ehu.bum4_restapi.utils;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.concurrent.Worker;
import javafx.fxml.FXML;
import javafx.scene.web.WebView;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.w3c.dom.events.Event;
import org.w3c.dom.events.EventListener;
import org.w3c.dom.events.EventTarget;
import org.w3c.dom.html.HTMLAnchorElement;

import java.awt.*;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;

public class HyperLinkRedirectListener implements ChangeListener<Worker.State>, EventListener
{
    private static final String CLICK_EVENT = "click";
    private static final String ANCHOR_TAG = "a";

    @FXML
    private final WebView webView;

    public HyperLinkRedirectListener(WebView webView)
    {
        this.webView = webView;
    }

    @Override
    public void changed(ObservableValue<? extends Worker.State> observable, Worker.State oldValue, Worker.State newValue)
    {
        if (Worker.State.SUCCEEDED.equals(newValue))
        {
            Document document = webView.getEngine().getDocument();
            NodeList anchors = document.getElementsByTagName(ANCHOR_TAG);
            for (int i = 0; i < anchors.getLength(); i++)
            {
                Node node = anchors.item(i);
                EventTarget eventTarget = (EventTarget) node;
                eventTarget.addEventListener(CLICK_EVENT, this, false);
            }
        }
    }

    @Override
    public void handleEvent(Event event)
    {
        HTMLAnchorElement anchorElement = (HTMLAnchorElement) event.getCurrentTarget();
        String href = anchorElement.getHref();

        if (Desktop.isDesktopSupported())
        {
            openLinkInSystemBrowser(href);
        }
        event.preventDefault();
    }

    private void openLinkInSystemBrowser(String url)
    {
        if( Desktop.isDesktopSupported() )
        {
            new Thread(() -> {
                try {
                    Desktop.getDesktop().browse( new URI(url) );
                } catch (IOException | URISyntaxException e1) {
                    e1.printStackTrace();
                }
            }).start();
        }

    }
}