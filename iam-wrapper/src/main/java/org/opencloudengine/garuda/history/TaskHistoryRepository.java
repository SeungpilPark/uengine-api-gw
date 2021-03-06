/*
 * Copyright (C) 2015 Bahamas Project (http://www.opencloudengine.org).
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package org.opencloudengine.garuda.history;

import java.util.List;

public interface TaskHistoryRepository {

    TaskHistory updateAsFailed(TaskHistory history);

    TaskHistory updateAsFinished(TaskHistory history);

    List<TaskHistory> selectByIdentifier(String identifier);

    TaskHistory selectById(String id);

    TaskHistory insert(TaskHistory history);

    TaskHistory updateById(TaskHistory history);

    void deleteById(String id);

    void deleteByIdentifier(String identifier);

}
