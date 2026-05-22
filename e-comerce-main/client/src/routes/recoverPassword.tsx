import { createFileRoute } from '@tanstack/react-router';

import RecoverPassword from '../lib/pages/recoverPassword';

export const Route = createFileRoute('/recoverPassword')({
  component: RecoverPassword,
});
