package com.pbw.ui;

import javax.swing.*;
import java.awt.*;

/**
 * Created by Krzysiek on 2016-01-30.
 */
public class MapSlider extends JSlider {

    public MapSlider() {
        super(JSlider.HORIZONTAL, 0, 1000, 0);

        Font font = new Font("Serif", Font.ITALIC, 15);

        this.setMajorTickSpacing(100);
        this.setMinorTickSpacing(10);
        this.setPaintTicks(true);
        this.setPaintLabels(true);
        this.setFont(font);
        this.setAlignmentY(Component.BOTTOM_ALIGNMENT);
    }
}
