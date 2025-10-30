import React from 'react';
import type { QueryQuery } from '../../../gen/gql/graphql';

export interface WidgetProps {
  entities: QueryQuery['entities'];
  spec: QueryQuery['layouts']['layouts'][0]['containers'][0];
}

export type WidgetComponent = React.FC<WidgetProps>;
