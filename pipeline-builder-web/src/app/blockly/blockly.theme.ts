import * as Blockly from 'blockly';
import { ITheme, Theme } from 'blockly/core/theme';

export const  CustomTheme = {
   base: Blockly.Themes.Classic,
   name: "custom_theme",
   startHats: true,
   blockStyles : {
      math_blocks: {
         colourPrimary: "#40bf4a",
         colourSecondary:"#c6e7ba",
         colourTertiary:"#3abf4a"
      },
      text_blocks: {
         colourPrimary: "#40bf4a",
         colourSecondary:"#c6e7ba",
         colourTertiary:"#3abf4a"
      },
      event_blocks: {
         colourPrimary: "#ffd500",
         colourSecondary:"#ffd000",
         colourTertiary:"#eed500"
      },
      control_blocks: {
         colourPrimary: "#ffab19",
         colourSecondary:"#ffab00",
         colourTertiary:"#eeab19"
      },
      logic_blocks: {
         colourPrimary: "#ffab19",
         colourSecondary:"#ffab00",
         colourTertiary:"#eeab19"
      },
      loop_blocks: {
         colourPrimary: "#ffab19",
         colourSecondary:"#ffab00",
         colourTertiary:"#eeab19"
      },
      operator_blocks: {
         colourPrimary: "#40bf4a",
         colourSecondary:"#c6e7ba",
         colourTertiary:"#3abf4a"
      },
      variable_blocks: {
         colourPrimary: "#ff8c1a",
         colourSecondary:"#ff8c00",
         colourTertiary:"#ee8c1a"
      },
      file_blocks: {
         colourPrimary: "#4c97ff",
         colourSecondary:"#a2c3f2",
         colourTertiary:"#ec97ff"
      },
      http_blocks: {
         colourPrimary: "#ad60d2",
         colourSecondary: "#d2b1e2",
         colourTertiary: "#7d60d2"
      },
      html_blocks: {
         colourPrimary: "#4169e1",
         colourSecondary: "#416900",
         colourTertiary: "#3169e1"
      }
   },
   categoryStyles : {
      file_operations: {
         colour: "#4c97ff"
      },
      control: {
         colour: "#ffab19"
      },
      events: {
         colour: "#ffd500"
      },
      data_operations: {
         colour: "#d65cd6"
      },
      operators: {
         colour: "#40bf4a",
      },
      variables: {
         colour: "#ff8c1a"
      },
      http: {
         colour: "#ad60d2"
      },
      html: {
         colour: "#4169e1"
      },
   },
   componentStyles : {
      workspaceBackgroundColour: '#efefef',
      //toolboxBackgroundColour: 'blackBackground',
      //toolboxForegroundColour: '#fff',
      //flyoutBackgroundColour: '#252526',
      //flyoutForegroundColour: '#ccc',
      //flyoutOpacity: 1,
   }
} as ITheme
