USE [suncoast]
GO

/****** Object:  Table [dbo].[LockerAssignment]    Script Date: 9/28/2016 9:50:35 AM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

SET ANSI_PADDING ON
GO

CREATE TABLE [dbo].[LockerAssignment](
	[LockerNum] [char](5) NOT NULL,
	[Serial] [int] NOT NULL,
 CONSTRAINT [PK_LockerAssignment] PRIMARY KEY CLUSTERED 
(
	[LockerNum] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

SET ANSI_PADDING OFF
GO

ALTER TABLE [dbo].[LockerAssignment]  WITH CHECK ADD  CONSTRAINT [FK_LockerAssignment_Lock] FOREIGN KEY([Serial])
REFERENCES [dbo].[Lock] ([Serial])
GO

ALTER TABLE [dbo].[LockerAssignment] CHECK CONSTRAINT [FK_LockerAssignment_Lock]
GO

