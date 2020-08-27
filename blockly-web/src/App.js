import React from 'react';
import BlocklyComponent, { Block, Value, Field, Shadow } from './Blockly';
import './App.css';

function App() {

  const workspaceRef = React.createRef();

  return (
    <div className="App">
      <header className="App-header">  
          Blockly Workspace Editor2
      </header>
      <BlocklyComponent ref={workspaceRef}
          readOnly={false} trashcan={true} media={'media/'}
          grid={{
            spacing: 20,
            length: 3,
            colour: '#ccc',
            snap: true
          }}
          move={{
            scrollbars: true,
            drag: true,
            wheel: true
          }}
          initialXml={`
<xml xmlns="http://www.w3.org/1999/xhtml">
<block type="controls_ifelse" x="0" y="0"></block>
</xml>
      `}>
                    
      </BlocklyComponent>
    </div>
  );
}

export default App;
