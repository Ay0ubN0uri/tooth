import React from 'react';
import { Route } from 'react-router-dom';

import ErrorBoundaryRoutes from 'app/shared/error/error-boundary-routes';

import Groupe from './groupe';
import GroupeDetail from './groupe-detail';
import GroupeUpdate from './groupe-update';
import GroupeDeleteDialog from './groupe-delete-dialog';

const GroupeRoutes = () => (
  <ErrorBoundaryRoutes>
    <Route index element={<Groupe />} />
    <Route path="new" element={<GroupeUpdate />} />
    <Route path=":id">
      <Route index element={<GroupeDetail />} />
      <Route path="edit" element={<GroupeUpdate />} />
      <Route path="delete" element={<GroupeDeleteDialog />} />
    </Route>
  </ErrorBoundaryRoutes>
);

export default GroupeRoutes;
