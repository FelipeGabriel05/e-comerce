import { QueryClient } from '@tanstack/react-query';
import axios from 'axios';

export const queryClient = new QueryClient();

export const api = axios.create({
  baseURL: 'http://localhost:8080',
  timeout: 1000,
  headers: {
    'Content-Type': 'application/json',
  },
});
