import { User } from 'next-auth';

export interface ServiceConfig {
  user: User;
  accessToken: string;
}
