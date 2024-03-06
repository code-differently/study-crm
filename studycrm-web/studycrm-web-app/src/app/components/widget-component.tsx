import React from 'react';

export interface WidgetProps {
  entities: Entity[];
  spec: Container|AnyWidget;
}

export type WidgetComponent = React.FC<WidgetProps>;
