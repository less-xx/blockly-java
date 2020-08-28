import React from 'react';
import BlocklyComponent, { Block, Value, Field, Shadow } from './Blockly';
import './App.css';
import workspaceXml from './Blockly/workspace';

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
          initialXml={workspaceXml}>
                    
      </BlocklyComponent>
    </div>
  );
}

export default App;
