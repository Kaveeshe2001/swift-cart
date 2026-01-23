import { createBrowserRouter } from 'react-router';
import App from './App';
import RootLayout from './layouts/root-layout';
import Home from './pages/(root)/home';
import AdminLayout from './layouts/admin-layout';
import Dashboard from './pages/(admin)/dashboard';

const router = createBrowserRouter([
  {
    Component: App,
    children: [
      {
        path: '/',
        Component: RootLayout,
        children: [{ index: true, Component: Home }],
      },
      {
        path: 'admin',
        Component: AdminLayout,
        children: [{ index: true, Component: Dashboard }],
      },
    ],
  },
]);

export default router;
