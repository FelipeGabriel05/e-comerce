import { createFileRoute } from '@tanstack/react-router';

export const Route = createFileRoute('/admin/falta-estoque')({
  component: RouteComponent,
});

function RouteComponent() {
  return <div>Hello "/admin/falta-estoque"!</div>;
}
