import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import PW from './pw';
import PWDetail from './pw-detail';
import PWUpdate from './pw-update';
import PWDeleteDialog from './pw-delete-dialog';

const PWRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<PW />} />
    <Route path="new" element={<PWUpdate />} />
    <Route path=":id">
      <Route index element={<PWDetail />} />
      <Route path="edit" element={<PWUpdate />} />
      <Route path="delete" element={<PWDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default PWRoutes;
