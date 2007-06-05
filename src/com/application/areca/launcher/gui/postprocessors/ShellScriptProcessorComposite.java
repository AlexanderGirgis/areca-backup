package com.application.areca.launcher.gui.postprocessors;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Text;

import com.application.areca.launcher.gui.Application;
import com.application.areca.launcher.gui.ProcessorEditionWindow;
import com.application.areca.postprocess.PostProcessor;
import com.application.areca.postprocess.ShellScriptPostProcessor;

/**
 * <BR>
 * @author Olivier PETRUCCI
 * <BR>
 * <BR>Areca Build ID : -6307890396762748969
 */
 
 /*
 Copyright 2005-2007, Olivier PETRUCCI.
 
This file is part of Areca.

    Areca is free software; you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation; either version 2 of the License, or
    (at your option) any later version.

    Areca is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.

    You should have received a copy of the GNU General Public License
    along with Areca; if not, write to the Free Software
    Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA  02110-1301  USA
 */
public class ShellScriptProcessorComposite extends AbstractProcessorComposite {

    private Text txt;
    
    public ShellScriptProcessorComposite(Composite composite, PostProcessor proc, final ProcessorEditionWindow window) {
        super(composite, proc, window);
        this.setLayout(new GridLayout(3, false));
        
        Label lbl = new Label(this, SWT.NONE);
        lbl.setText(RM.getLabel("procedition.scriptfile.label"));
        
        txt = new Text(this, SWT.BORDER);
        txt.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));
        window.monitorControl(txt);
        
        Button btnBrowse = new Button(this, SWT.PUSH);
        btnBrowse.setText(RM.getLabel("common.browseaction.label"));
        btnBrowse.addListener(SWT.Selection, new Listener() {
            public void handleEvent(Event event) {
                String path = Application.getInstance().showFileDialog(txt.getText(), window);
                if (path != null) {
                    txt.setText(path);
                }
            }
        });
        
        if (proc != null) {
            ShellScriptPostProcessor sProc = (ShellScriptPostProcessor)proc;
            txt.setText(sProc.getCommand());
        }
    }

    public void initProcessor(PostProcessor proc) {
        ShellScriptPostProcessor fProc = (ShellScriptPostProcessor)proc;
        fProc.setCommand(txt.getText());
    }
    
    public boolean validateParams() {
        window.resetErrorState(txt);
        
        if (
                txt.getText() == null 
                || txt.getText().trim().length() == 0 
        ) {
            window.setInError(txt);
            return false;
        }

        return true;
    }
}
