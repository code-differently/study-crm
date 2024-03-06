import React from 'react';
import TableWidget from './table-widget';
import { WidgetComponent, WidgetProps } from './widget-component';

const Widgets: Record<string, WidgetComponent> = {
  table: TableWidget,
};

export default function Widget({ entities, spec }: WidgetProps) {
  const widgetType = spec.type || '';
  if (Widgets[widgetType] != null) {
    return React.createElement(Widgets[widgetType], { entities, spec });
  }
  return <div>some widget</div>;
}
