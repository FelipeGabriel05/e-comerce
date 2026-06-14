import { createFileRoute } from '@tanstack/react-router';

export const Route = createFileRoute('/admin/valor-recebido')({
  component: RouteComponent,
});

function RouteComponent() {
  return <div>Hello "/admin/valor-recebido"!</div>;
}
